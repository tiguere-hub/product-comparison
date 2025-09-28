pipeline {
    environment {
        JAVA_TOOL_OPTIONS = "-Duser.home=/home/jenkins"
    }
    agent {
        docker {
                    image 'gradle:jdk21'
                    args '-v /tmp/gradle-cache:/home/jenkins/.gradle'
                }
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/tiguere-hub/product-comparison.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                // Compilar el proyecto usando Maven
                sh './gradlew clean build --no-daemon'
            }
        }
    }
    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}