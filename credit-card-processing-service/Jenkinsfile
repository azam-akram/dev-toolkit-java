pipeline{
    agent any
    
    parameters {
        choice(choices: ['dev','prod'], name: 'ENV_NAME', description: 'Environment name')
    }
    
    environment {
        GIT_REPO_URL = 'https://github.com/azam-akram/credit-card-processing-service.git'
    }

    stages {
        stage ("Git checkout") {
            steps {
                echo "Checking out code from GIT";
                git "${GIT_REPO_URL}"
            }
        }

        stage ("Building") {
            steps {
                echo "Building the source code";
                //bat label: '', script: 'gradlew clean build'
            }
        }

        stage ("Unit testing") {
            steps {
                echo "Unit testing";
                bat label: '', script: 'gradlew check'
            }
        }
        
        stage('Sanity check') {
            steps {
                input "Does the staging environment look ok?"
            }
        }

        stage ("Deploying") {
            steps {
                echo "Deploying project on ${ENV_NAME}";
                //bat label: '', script: 'docker-compose up'
            }
        }
    }
    post {
        always {
            echo 'This will always run'
            deleteDir()
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}
