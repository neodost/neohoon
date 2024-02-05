pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        git(url: 'https://github.com/kkkqwerasdf123/neohoon', branch: 'master', changelog: true)
      }
    }

    stage('') {
      steps {
        tool 'corretto-17'
        sh './gradlew clean api:build'
      }
    }

  }
}