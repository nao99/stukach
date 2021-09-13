# NDBS FileSystem

[![Author](https://img.shields.io/badge/author-@nao99-green.svg)](https://github.com/nao99)
[![Source Code](https://img.shields.io/badge/source-ndbs/filesystem-blue.svg)](https://github.com/nao99/stukach/tree/master/common-filesystem)
[![Software License](https://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/nao99/stukach/tree/master/common-filesystem/LICENSE)

## About NDBS FileSystem

NDBS FileSystem - is ease to use filesystem abstraction. Allows you to manage
files from any sources such as Amazon S3, Microsoft Azure etc.

### How to use

Just instantiate a **FileSystemService**, build needed **Path**(s) (to do that you can use a **PathStrategy**),
create a **FileSystemResource** abstraction and use any method from the **FileSystem**

If you want to use not at all local filesystems, then you should create (or implement already existed) **FileSystem**,
and create a **FileSystemResource** using **Path** from that **FileSystem**

### Useful

For Amazon S3 case you can implement the [Amazon-S3-FileSystem-NIO2](https://github.com/Upplication/Amazon-S3-FileSystem-NIO2)
library
