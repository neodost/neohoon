pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        git(url: 'https://github.com/kkkqwerasdf123/neohoon', branch: 'master', changelog: true)
      }
    }

    stage('build') {
      steps {
        tool 'corretto-17'
        sh '''chmod +x gradlew
./gradlew clean api:build'''
      }
    }

  }
}