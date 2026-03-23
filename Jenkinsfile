pipeline {
  agent any

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
        sh 'docker compose build --no-cache'
        echo 'Images Docker construites'
      }
    }

    stage('Déploiement') {
      steps {
        sh 'docker compose up -d --remove-orphans'
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