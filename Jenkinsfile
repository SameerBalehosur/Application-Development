pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo "Build Stage"
//                 sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                echo "Test Stage"
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploy Stage"
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml' // Publish test results
        }
        failure {
            echo "Pipeline failed due to errors."
        }
    }
}