pipeline {
    agent any
    environment {
        registryCredential = 'ecr:us-east-1:awscreds'
        appRegistry = "538808576863.dkr.ecr.us-east-1.amazonaws.com/jenkinsecr"
        vprofileRegistry = "https://538808576863.dkr.ecr.us-east-1.amazonaws.com"
        SONARHOST = "https://usplsvulx1005.elabs.svcs.entsvcs.com"
	SONARTOKEN = credentials("sonarkey")
        cluster = "ecsclus"
        service = "serviceappecs"
    }
    tools{
        maven 'maveninstall'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/harsha5401/springdemo']]])
                sh "mvn -B -DskipTests clean package"
            }
        }
	 // stage('Unit Test') {
  //           steps {
  //               sh 'mvn test'
  //               sh 'mvn surefire-report:report'
  //           }
  //           post {
  //               always {
  //                 sh 'echo save unit test results'
  //                 junit '**/target/surefire-reports/TEST-*.xml'	           
  //               }
  //           }
  //       } 	       
        stage('static analysis') {
            steps {
                sh 'echo static analysis'
               
        	sh "mvn jacoco:report sonar:sonar -Dsonar.host.url=$SONARHOST -Dsonar.login=$SONARTOKEN"
		    
		//archiveArtifacts artifacts: '**/target/sonar/report-task.txt'    

            }
	}
	    
	    //quality gate for sonarqube
	    stage('quality gate') {
	    	    steps {           
	    	       sh 'bash -x gate.sh $SONARHOST $SONARTOKEN'
	    	    }
	    }	    
         stage('Build App Image') {
       steps {
       
         script {
                dockerImage = docker.build( appRegistry + ":$BUILD_NUMBER", ".")
             }

     }
    
    }

    // stage('Upload App Image') {
    //       steps{
    //         script {
    //           docker.withRegistry( vprofileRegistry, registryCredential ) {
    //             dockerImage.push("$BUILD_NUMBER")
    //             dockerImage.push('latest')
    //           }
    //         }
    //       }
    //  }
    //   stage('Deploy to ecs') {
    //       steps {
    //     withAWS(credentials: 'awscreds', region: 'us-east-1') {
    //       sh 'aws ecs update-service --cluster ${cluster} --service ${service} --force-new-deployment'
    //     }
    //   }  
    //   }
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
