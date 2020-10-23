# Azure Function as a Service Demo
## About
Azure Functions is an event-driven serverless compute platform that can solve complex orchestration problems. 
Build and debug locally without additional setup, deploy and operate at scale in the cloud, and integrate services using triggers and bindings.
This example uses an HTTP trigger to create a REST API that can detect sentiment in a text made of sentences. Accepts text as a query parameter in a GET request or submit as POST with JSON in the body.
## Text Analytics
Discover insights in unstructured text using natural language processing (NLP)—no machine learning expertise required. Identify key phrases and entities such as people, places, and organizations to understand common topics and trends. Classify medical terminology using domain-specific, pretrained models. Gain a deeper understanding of customer opinions with sentiment analysis. Evaluate text in a wide range of languages. This example uses the Document Sentiment Analyzer.

## Setup
* Setup a function app. Sample Azure Resource Manager template is included in the code. e.g az deployment group create --name hkfass --resource-group HK-FaaS-Demo --template-file template.json --parameters parameters.json
* Setup a Congitive Services account with Text Analytics.
* Add app configuration settings TEXT_ANALYTICS_ENDPOINT & TEXT_ANALYTICS_KEY
* Refer documentation and setup your Azure Functions Runtime locally.
* Check out the code.
* Run mvn clean package -DskipTests
* Run mvn azure-functions:run to test it locally, make sure to setup env variables mentioned above.
* Run mvn azure-functions:deploy to deploy to Funtion App in the cloud.
