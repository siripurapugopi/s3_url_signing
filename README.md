S3URLSigning Class offers methods to pre-sign URLs of Get, Put requests.

To get pre-signed URL for get object request use
S3URLSigning.getS3ObjectSignedURL(Bucket, Object_Key, Region, Expiry in seconds defaults to 600)

To get pre-signed URL for put object request use
S3URLSigning.putS3ObjectSignedURL(Bucket, Object_Key, Region, Expiry in seconds defaults to 600)

AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY must be exported to environment.

Tested on java version 1.8.0_141 and above