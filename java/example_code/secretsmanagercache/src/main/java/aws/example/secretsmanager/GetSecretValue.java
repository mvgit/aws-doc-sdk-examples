/**
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
import com.amazonaws.secretsmanager.caching.SecretCache;

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
  }

  public static void getSecret(String secretName) {

     SecretCache cache = new SecretCache();
     String secretCache = cache.getSecretString(secretName);
     System.out.println("Secret Value from new secrert cache lib: " + secretCache);

  }

}
