pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        tool 'corretto-17'
        sh '''chmod +x gradlew
./gradlew clean api:build'''
      }
    }

  }
}