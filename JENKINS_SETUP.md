# Jenkins Pipeline Setup Guide

This guide explains how to configure Jenkins to run the declarative pipeline for the Inventory Management REST API.

## Prerequisites

1. Jenkins installed and running
2. JDK 17 installed on Jenkins server
3. Maven installed on Jenkins server

## Jenkins Configuration

### 1. Install Required Plugins

Go to **Manage Jenkins** → **Manage Plugins** → **Available** and install:

- **Pipeline** (usually pre-installed)
- **JUnit Plugin** (for test result reporting)
- **Maven Integration Plugin** (for Maven support)

### 2. Configure JDK 17

1. Go to **Manage Jenkins** → **Global Tool Configuration**
2. Under **JDK**, click **Add JDK**
3. Name it: `JDK17`
4. Set **JAVA_HOME** to your JDK 17 installation path:
   - Windows: `C:\Program Files\Java\jdk-17`
   - Linux/Mac: `/usr/lib/jvm/java-17-openjdk` or similar
5. Click **Save**

### 3. Configure Maven

1. In **Global Tool Configuration**, scroll to **Maven**
2. Click **Add Maven**
3. Name it: `Maven`
4. Choose one of:
   - **Install automatically** (recommended) - Jenkins will download Maven
   - **MAVEN_HOME** - Point to your existing Maven installation
5. Click **Save**

## Pipeline Stages

The Jenkinsfile includes the following stages:

1. **Checkout**: Checks out source code from SCM (Git)
2. **Build**: Compiles the project using Maven
3. **Test**: Runs unit tests and publishes results
4. **Package**: Creates the JAR artifact

## Creating a Jenkins Job

### Option 1: Pipeline Job from SCM

1. Click **New Item** in Jenkins
2. Enter a name (e.g., "Inventory-Management-API")
3. Select **Pipeline**
4. Click **OK**
5. In the pipeline configuration:
   - **Definition**: Select **Pipeline script from SCM**
   - **SCM**: Select **Git**
   - **Repository URL**: Enter your Git repository URL
   - **Branch**: `*/main` or `*/master` (or your branch name)
   - **Script Path**: `Jenkinsfile`
6. Click **Save**
7. Click **Build Now** to run the pipeline

### Option 2: Multibranch Pipeline

1. Click **New Item**
2. Enter a name
3. Select **Multibranch Pipeline**
4. Configure:
   - **Branch Sources**: Add your Git repository
   - Jenkins will automatically detect and build branches with a Jenkinsfile
5. Click **Save**

## Pipeline Features

- **Build History**: Keeps last 10 builds
- **Timeout**: 30 minutes maximum build time
- **Timestamps**: All log entries are timestamped
- **Test Reporting**: JUnit test results are published
- **Artifact Archiving**: JAR files are archived after successful builds

## Troubleshooting

### JDK 17 Not Found

If you see "JDK17 tool not found":
- Verify JDK 17 is configured in **Global Tool Configuration**
- Ensure the name matches exactly: `JDK17`

### Maven Not Found

If Maven commands fail:
- Verify Maven is configured in **Global Tool Configuration**
- Check that Maven is in the system PATH
- Try using the full path: `/path/to/mvn` instead of `mvn`

### Test Failures

- Check the **Test Results** section in the build page
- Review console output for specific test failures
- Ensure all dependencies are available

## Environment Variables

The pipeline sets:
- `MAVEN_OPTS`: Maven memory settings
- `JAVA_HOME`: Points to JDK 17
- `GIT_COMMIT_SHORT`: Short Git commit hash

## Customization

You can customize the Jenkinsfile to:
- Add deployment stages
- Integrate with Docker
- Add code quality checks (SonarQube, etc.)
- Add notification steps (Slack, email, etc.)

