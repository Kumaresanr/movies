pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script{
                if (isUnix()) {
                    sh './gradlew clean build'
                     } else {
                        bat 'gradlew clean build -x test'
                      }
                  }

            }

        }
        stage ('Unit test') {
            steps {
                script {
                     if (isUnix()) {
                        sh './gradlew test'
                     } else {
                        bat 'gradlew test'
                      }
                  }
            }
        }
	stage ('Docker test') {
            steps {
                script {
                    bat 'docker build . -t com.keycloakapp/service'
                }
            }
        }
	stage ('Docker run')
	    steps {
		    script {
			    bat 'docker run --name testDocker --restart=always -p 8082:8082 com.keycloakapp/service' 
		    }
	    }
    	}
        stage('Packaging') {
            steps{
                script{
                    if(isUnix()) {
                        sh './gradlew bootWar -Pprod'
                     } else {
                        bat 'gradlew bootWar -Pprod'
                        }
                    }
                  }
        }
        stage('Artifacts') {
            steps {
                archiveArtifacts artifacts: '**/build/libs/*.war'
            }
        }
        stage('Deploy') {
            steps {
                script {
                    bat 'copy /y "C:\\Program Files (x86)\\Jenkins\\workspace\\restservice-pipeline\\build\\libs\\service.war" "C:\\Program Files\\wildfly-11.0.0.Final\\standalone\\deployments"'
                    }
            }
        }
    }

