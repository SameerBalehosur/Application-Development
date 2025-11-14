pipeline {
    agent any

    tools {
        maven 'M3'   // same name as you gave in Jenkins Tools
        jdk 'JDK17'  // (optional but recommended)
    }

    stages {
        stage('Build') {
            steps {
                echo "Building..."
                bat 'mvn clean compile'   // 👈 use 'bat' for Windows agent
            }
        }

        stage('Test') {
            steps {
                echo "Running tests..."
                bat 'mvn test'
            }
        }
    }

    post {
        always {
//             junit 'target/surefire-reports/*.xml'
        }
    }
}
