//snippet-sourcedescription:[GetSecretValue.java demonstrates how to get the value of a secret from AWS Secrets Manager.]
//snippet-keyword:[Java]
//snippet-sourcesyntax:[java]
//snippet-keyword:[Code Sample]
//snippet-service:[secretsmanager]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[]
//snippet-sourceauthor:[Walker]
/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package aws.example.secretsmanager;
import java.nio.ByteBuffer;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.*;
import com.amazonaws.services.secretsmanager.model.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;	
public class GetSecretValue {
    
  
  public static void main(String[] args) {

    final String usage = "\n" +
    "Usage:\n" +
    "    <secretName> \n\n" +
    "Where:\n" +
    "    secretName - The name of the secret (for example, tutorials/MyFirstSecret). \n";

    if (args.length != 1) {
        System.out.println(usage);
        System.exit(1);
    }

    String secretName = args[0];

    getSecret(secretName);
    getSecret();
  }

  public static void getSecret(String secretName) {

      String endpoint = "secretsmanager.us-east-1.amazonaws.com";
      String region = "us-east-1";

      AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
      AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
      clientBuilder.setEndpointConfiguration(config);
      AWSSecretsManager client = clientBuilder.build();

      String secret;
      ByteBuffer binarySecretData;
      GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
              .withSecretId(secretName).withVersionStage("AWSCURRENT");
      GetSecretValueResult getSecretValueResult = null;
      try {
          getSecretValueResult = client.getSecretValue(getSecretValueRequest);

      } catch(ResourceNotFoundException e) {
          System.out.println("The requested secret " + secretName + " was not found");
      } catch (InvalidRequestException e) {
          System.out.println("The request was invalid due to: " + e.getMessage());
      } catch (InvalidParameterException e) {
          System.out.println("The request had invalid params: " + e.getMessage());
      }

      if(getSecretValueResult == null) {
          return;
      }

      // Depending on whether the secret was a string or binary, one of these fields will be populated
      if(getSecretValueResult.getSecretString() != null) {
          secret = getSecretValueResult.getSecretString();
          System.out.println(secret);
      }
      else {
          binarySecretData = getSecretValueResult.getSecretBinary();
          System.out.println(binarySecretData.toString());
      }
  }

  // Use this code snippet in your app.
// If you need more information about configurations or implementing the sample
// code, visit the AWS docs:
// https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html

// Make sure to import the following packages in your code
// import software.amazon.awssdk.regions.Region;
// import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
// import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
// import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;	

public static void getSecret() {

    String secretName = "chc/rds/oracle/fdb3";
    Region region = Region.of("us-east-1");

    // Create a Secrets Manager client
    SecretsManagerClient client = SecretsManagerClient.builder()
            .region(region)
            .build();

    GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
            .secretId(secretName)
            .build();

    GetSecretValueResponse getSecretValueResponse;

    try {
        getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
    } catch (Exception e) {
        // For a list of exceptions thrown, see
        // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
        throw e;
    }

    String secret = getSecretValueResponse.secretString();
    System.out.println(secret);

    // Your code goes here.
}

}
