package com.turku.common

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import kotlin.io.path.Path

fun signBucket(
    presigner: S3Presigner, bucketName: String, keyName: String, contentType: String, accessLevel: String = "private"
): String {

    try {
        val override = AwsRequestOverrideConfiguration.builder().putRawQueryParameter("x-amz-acl", accessLevel).build()

        val objectRequest: PutObjectRequest =
            PutObjectRequest.builder().bucket(bucketName).key(keyName).contentType(contentType)
                .overrideConfiguration(override).build()

        val presignRequest: PutObjectPresignRequest =
            PutObjectPresignRequest.builder().signatureDuration(Duration.ofMinutes(10)).putObjectRequest(objectRequest)
                .build()

        val presignedRequest: PresignedPutObjectRequest = presigner.presignPutObject(presignRequest)

        return presignedRequest.url().toString()
    } catch (error: S3Exception) {
        ApplicationLogger.app("signed url exception", error.stackTraceToString())
    }

    return "UNABLE_TO_CREATE_SIGNED_URL"
}

fun signedUrlS3(keyName: String, contentType: String): String {

    val awsCreds = AwsBasicCredentials.create(
        Utils.getEnv("ktor.aws.accessKey"), Utils.getEnv("ktor.aws.secretKey")
    )

    val bucketName: String = Utils.getEnv("ktor.aws.bucket")
    val region: Region = AwsRegion.getRegion(Utils.getEnv("ktor.aws.s3Region").toInt())
    val presigner =
        S3Presigner.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build()

    val url = signBucket(presigner, bucketName, keyName, contentType)
    presigner.close()

    return url
}

fun uploadToS3(keyName: String, filePath: String, contentType: String, accessLevel: String = "private") {

    val awsCreds = AwsBasicCredentials.create(
        Utils.getEnv("ktor.aws.accessKey"), Utils.getEnv("ktor.aws.secretKey")
    )

    val bucketName: String = Utils.getEnv("ktor.aws.bucket")
    val region: Region = AwsRegion.getRegion(Utils.getEnv("ktor.aws.s3Region").toInt())

    val override = AwsRequestOverrideConfiguration.builder().putRawQueryParameter("x-amz-acl", accessLevel).build()

    val objectRequest: PutObjectRequest =
        PutObjectRequest.builder().bucket(bucketName).key("${Utils.getEnv("ktor.aws.bucketPrefix")}/${keyName}")
            .contentType(contentType).overrideConfiguration(override).build()

    val client =
        S3Client.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build()

    client.putObject(objectRequest, Path(filePath))
}
