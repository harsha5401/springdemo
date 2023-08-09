pipeline {
    agent any
    environment {
        registryCredential = 'ecr:us-east-1:awscreds'
        appRegistry = "538808576863.dkr.ecr.us-east-1.amazonaws.com/jenkinsecr"
        vprofileRegistry = "https://538808576863.dkr.ecr.us-east-1.amazonaws.com"
    }
    tools{
        maven 'maveninstall'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/harsha5401/springdemo']]])
                sh 'mvn clean install'
            }
        }
         stage('Build App Image') {
       steps {
       
         script {
                dockerImage = docker.build( appRegistry + ":$BUILD_NUMBER", ".")
             }

     }
    
    }

    stage('Upload App Image') {
          steps{
            script {
              docker.withRegistry( vprofileRegistry, registryCredential ) {
                dockerImage.push("$BUILD_NUMBER")
                dockerImage.push('latest')
              }
            }
          }
     }

//         stage('Build docker image'){
//             steps{
//                 script{
//                     sh 'docker build -t harsha7633/springboat .'
//                 }
//             }
//         }
//         stage('Push image to Hub'){
//             steps{
//                 script{
//                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
//                    sh 'docker login -u harsha7633 -p ${dockerhubpwd}'

// }
//                    sh 'docker push harsha7633/springboat'
//                     sh 'docker run -itd -p 9122:8080 harsha7633/springboat'
//                 }
//             }
//         }
        // stage('Deploy to k8s'){
        //     steps{
        //         script{
        //             kubernetesDeploy (configs: 'deploymentservice.yaml',kubeconfigId: 'k8sconfigpwd')
        //         }
        //     }
        // }
    }
}
