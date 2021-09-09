package org.ndbs.filesystem.domain;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import org.ndbs.filesystem.domain.model.FilesystemResource;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * AwsFilesystemService class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
public class AwsFilesystemService implements FilesystemService {
    private final AmazonS3 client;

    public AwsFilesystemService(AmazonS3 client) {
        this.client = client;
    }

    @Override
    public void copy(FilesystemResource source, FilesystemResource destination) throws FilesystemException {
        if (destination.equals(source)) {
            return;
        }

        var sourceFilesystem = source.getFilesystem().toString();
        var destinationFilesystem = destination.getFilesystem().toString();

        var sourcePath = source.getAbsolutePath();
        var destinationPath = destination.getAbsolutePath();

        try {
            if (source.isLocal()) {
                var file = new File(source.getAbsolutePath());
                client.putObject(destinationFilesystem, destinationPath, file);

                return;
            }

            client.copyObject(sourceFilesystem, sourcePath, destinationFilesystem, destinationPath);
        } catch (AmazonClientException e) {
            throw new FilesystemException(e);
        }
    }

    @Override
    public void move(FilesystemResource source, FilesystemResource destination) throws FilesystemException {
        copy(source, destination);
        delete(source);
    }

    @Override
    public void delete(FilesystemResource source) throws FilesystemException {
        try {
            client.deleteObject(source.getFilesystem().toString(), source.getAbsolutePath());
        } catch (AmazonClientException e) {
            throw new FilesystemException(e);
        }
    }

    // TODO: Improve this method
    @Override
    public URL getUrl(FilesystemResource source) throws FilesystemException {
        var expiration = new Date();

        var expirationTime = expiration.getTime() + 1000 * 60 * 60;
        expiration.setTime(expirationTime);

        try {
            return client.generatePresignedUrl(source.getFilesystem().toString(), source.getAbsolutePath(), expiration);
        } catch (AmazonClientException e) {
            throw new FilesystemException(e);
        }
    }
}
