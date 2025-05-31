# QR Code Generator

This project is a simple QR Code generator where you pass on some data (e.g., a link)
and a QR Code image is returned. The QR Code image is generated locally, then is sent
to an Amazon S3 bucket, and what is returned is the link to the image in the said S3 
bucket.


## What's used

- Spring (WEB + Dev Tools)
- Amazon AWS SDK (S3)
- Google ZXing (core + javase)

## Setup

Before using you'll need to set up some things to make the AWS S3 connection work,
which are some environment variables:
- `AWS_ACCESS_KEY_ID`: The AWS Access Key ID
- `AWS_SECRET_ACCESS_KEY`: The AWS Secret Access Key
- `AWS_REGION`: The AWS Region in which your bucket is stored
- `AWS_S3_BUCKET_NAME`: the AWS S3 Bucket Name

To pass them, you can either create something like a '.env' file in the project root
or pass them one by one when starting the Docker container (examples below).

## How's used

You'll use Docker to run the project. You'll open a terminal on the root of the
project and will build an image by typing:

`docker build -t your_image_name:your_image_version_number .`

It's up to you to give the image a name and version, even though they aren't mandatory.
And yes, the dot '.' at the end makes part of the command, with it, you're telling 
Docker that the Dockerfile is in the same directory that the command line is open.

After that, it's time to run the container, but in order to do that, you'll need to
tell Docker about the content of the environment variables presented in the "Setup"
section. In here you have two main options: pass the variables one by one via the 
command line or set a file with them and tell Docker to look into it.

- Passing a file with the variables (the file can have any name). Below, the file is
considered to be in the root of the project, adapt accordingly:

`docker run --env-file file_name -p 8080:8080 your_image_name:your_image_version_number`

- Passing variables on the command line:

`docker run
-e AWS_ACCESS_KEY_ID=value -e AWS_SECRET_ACCESS_KEY=value
-e AWS_REGION=value -e AWS_S3_BUCKET_NAME=value -p 8080:8080
your_image_name:your_image_version_number`

After the container's up, you can use his REST endpoint. It exposes a single endpoint:
`/gen-qrcode`. You'll need to `POST` it with the following body:

```json
{
  "data": "your_link"
}
```

If everything's alright, you must receive a `200 OK` response with a body similar to this:

```json
{
  "link": "https://your_bucket_name.s3.your_region.amazonaws.com/uuid_file_name"
}
```