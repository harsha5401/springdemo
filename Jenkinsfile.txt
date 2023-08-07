pipeline {
    agent any
    tools{
        maven 'maveninstall'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/harsha5401/devops-automation']]])
                sh 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t harsha7633/springboat .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                   sh 'docker login -u harsha7633 -p ${dockerhubpwd}'

}
                   sh 'docker push harsha7633/springboat'
                    sh 'docker run -itd -p 9122:8080 harsha7633/springboat'
                }
            }
        }
        // stage('Deploy to k8s'){
        //     steps{
        //         script{
        //             kubernetesDeploy (configs: 'deploymentservice.yaml',kubeconfigId: 'k8sconfigpwd')
        //         }
        //     }
        // }
    }
}
