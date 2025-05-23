 # ML Predict API
 This repository provides a lightweight RESTful API for user behavior prediction based on a trained ONNX model. The backend is implemented in Java using Spring Boot, with support for HTTPS and containerized deployment via Docker.You can use this api to predict user next behavior base on the previous user interaction.The model used is traditional lstm model trained by dataset from kaggle :Retailrocket Recommender System Dataset.
 ##  Setup & Deployment

 ### Requirements

 - Docker
 - Git, Java 17, Maven for local development(optional)

 ### Quick Start

 for quick start you can use docker hub image by simplily running command in cmd:
    
 ```bash
  docker run -d -p 443:443 tfjstfjs666/ml-predict-api 
 ```

for docker hub image: https://hub.docker.com/r/tfjstfjs666/ml-predict-api
 
 use command(docker ps) to check if the container is running properly，then you can interact with the api over HTTPS

 #### for quick test this is a example of payload：
    {
     "sequence": [
       [
         [12.5, 1, 0, 1, 5342],
         [0.0, 2, 0, 0, 8213],
         [8.1, 3, 1, 1, 4420],
         [5.6, 4, 0, 0, 1123],
         [3.4, 5, 0, 0, 6721]
       ]
     ]
    }
#### you should see response like this format:
    {
      "prediction": 2,
      "probabilities": [0.01, 0.03, 0.96]
    }

you can also use the curl to test the service by typing command after the container is running:

    
```bash 
curl -k -X POST https://localhost/predict_behavior -H "Content-Type: application/json" -d "{\"sequence\": [[[12.5,1,0,1,5342],[0.0,2,0,0,8213],[8.1,3,1,1,4420],[5.6,4,0,0,1123],[3.4,5,0,0,6721]]]}" 
```
##  Branch Structure

| Branch       | Description                                                                                                     |
|--------------|-----------------------------------------------------------------------------------------------------------------|
| `main`       | Contains README and the whole file of this project, use it to deploy the project locally if you perfer git clone|
| `model-dev`  | Jupyter notebooks, model training logs, and data for training the model, use for model develop and improvement  |
| `api-java`   | Java Spring Boot API codebase for ONNX inference,use it for java api develoment                                 |
| `docker`     | Dockerfile, TLS setup, and deployment configuration                                                             |


##  Author & Reflections

**Author:** [@tfjstfjs](https://github.com/tfjstfjs)
This project was a hands-on implementation of deploying machine learning inference using Java and ONNX in a real-world containerized setting included support TLS encrypted transmission. It is a good practice in:
- Jupyernotebook for model development
- Docker image structuring for production
- TLS setup with java SSL(you can also enable nignx, file also included.)
- Spring Boot service configuration with ONNX integration
