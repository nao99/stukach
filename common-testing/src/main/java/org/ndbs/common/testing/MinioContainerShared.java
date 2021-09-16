package org.ndbs.common.testing;

/**
 * MinioContainerShared class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-16
 */
public class MinioContainerShared extends MinioContainer<MinioContainerShared> {
    private static final String MINIO_IMAGE    = "minio/minio:RELEASE.2020-10-28T08-16-50Z-51-g7fdffa036";
    private static final String MINIO_MC_IMAGE = "minio/mc:RELEASE.2021-07-27T06-46-19Z";

    private static MinioContainerShared container;

    private MinioContainerShared() {
        super(MINIO_IMAGE, MINIO_MC_IMAGE);
    }

    public static MinioContainerShared getInstance() {
        if (container == null) {
            container = new MinioContainerShared();
        }

        return container;
    }
}
