pipeline {
    agent any
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                sh 'mvn clean package  -P jenkins'
            }
        }

        stage ('Test') {
            steps {
                sh 'mvn clean test cobertura:cobertura'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }

        stage ('Deploy') {
            steps {
                sh 'echo deploy'
            }
        }
    }
}