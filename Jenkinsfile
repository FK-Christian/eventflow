pipeline {
  agent any

  tools {
   maven 'maven3'  // must match the name you set above
  }
  stages {
    stage('Checkout') {
      steps {
        checkout scm
        echo 'Code récupéré depuis GitHub'
      }
    }

    stage('Tests') {
      steps {
        sh 'cd order-service && mvn test -q'
        sh 'cd inventory-service && mvn test -q'
        echo 'Tests passés'
      }
    }

    stage('Build Docker') {
      steps {
        sh 'docker compose -p eventflow build --no-cache order-service inventory-service notification-service'
        echo 'Images Docker construites'
      }
    }

    stage('Déploiement') {
      steps {
        sh '''
            docker compose -p eventflow up -d --no-deps \
              order-service \
              inventory-service \
              notification-service
        '''
        echo 'Services démarrés'
      }
    }
  }

  post {
    success {
      echo 'Pipeline OK — tous les services tournent'
    }
    failure {
      echo 'Pipeline ECHEC — vérifier les logs'
    }
  }
}