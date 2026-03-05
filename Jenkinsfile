pipeline {
    agent any
    
    tools {
        maven 'maven_home'
        
    }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
    }
    
    environment {
        MAVEN_OPTS = '-Xmx1024m'
        JAVA_HOME = "${tool 'java17'}"
        git -"git.exe"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code from repository...'
                checkout scm
                script {
                    def gitCommitOutput = bat(returnStdout: true, script: '@echo off && git rev-parse --short HEAD').trim()
                    // Extract just the commit hash from the output (removes command prompt text)
                    def lines = gitCommitOutput.split('\r?\n')
                    def gitCommit = lines.find { line -> line.matches(/^[a-f0-9]{7,}$/) } ?: lines.last().replaceAll(/.*>/, '').trim()
                    env.GIT_COMMIT_SHORT = gitCommit
                    echo "Git commit: ${gitCommit}"
                }
            }
        }
        stages {
        stage('Checkout1') {
            steps {
                echo 'Checking out source code from repository...'
                checkout scm
                script {
                    def gitCommitOutput = bat(returnStdout: true, script: '@echo off && git rev-parse --short HEAD').trim()
                    // Extract just the commit hash from the output (removes command prompt text)
                    def lines = gitCommitOutput.split('\r?\n')
                    def gitCommit = lines.find { line -> line.matches(/^[a-f0-9]{7,}$/) } ?: lines.last().replaceAll(/.*>/, '').trim()
                    env.GIT_COMMIT_SHORT = gitCommit
                    echo "Git commit: ${gitCommit}"
                }
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building the project with Maven...'
                bat 'mvn clean compile -DskipTests'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running unit tests...'
                bat 'mvn test'
            }
            post {
                always {
                    echo 'Publishing test results...'
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo 'Packaging the application...'
                bat 'mvn package -DskipTests'
            }
            post {
                success {
                    echo 'Package created successfully!'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
            script {
                def artifactList = bat(returnStdout: true, script: 'dir /b target\\*.jar').trim()
                if (artifactList) {
                    def artifacts = artifactList.split('\r?\n')
                    echo "Artifact(s) created: ${artifacts.join(', ')}"
                }
            }
        }
        failure {
            echo 'Pipeline failed! Check the console output for details.'
        }
        unstable {
            echo 'Pipeline is unstable! Some tests may have failed.'
        }
    }
}

