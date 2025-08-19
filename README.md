# CCS3308 – Assignment 1 22ug2-0023  
# Thavisha Nipun Bandara
## Java Task Scheduler (Spring Boot) + PostgreSQL (Persistent Storage)

---

##  Deployment Requirements
To deploy and run this application, you need the following software installed on your system (Ubuntu recommended):

- **Docker Engine** (v24.x or newer) 
- **Docker Compose v2** (bundled with recent Docker versions) 
- **Git** (to clone and submit the repository) 
- **Java 17** *(only required if running the app outside Docker)*

---

## About the application
The **Task Scheduler Web Application** is a lightweight planner tool built with **Java Spring Boot**. 

- Users can **add tasks** (e.g., “Doctor’s appointment at 10 AM”). 
- Tasks are stored in a **PostgreSQL database**. 
- A **persistent host folder (`./pgdata`)** is used so that data is not lost when containers stop or are removed. 
- The **web interface** shows all scheduled tasks in reverse order. 

---

##  Network and Volume Details
- **Network**: 
  - Custom Docker bridge network: `myapp-net`. 
  - Ensures communication between containers. 

- **Persistent Volume**: 
  - Implemented as a **bind mount**. 
  - Host folder `./pgdata` → `/var/lib/postgresql/data` inside PostgreSQL container. 
  - Provides persistent storage outside the container lifecycle. 

---

## Container Configuration
### 1. PostgreSQL Database (`db`)
- **Image**: `postgres:15-alpine` 
- **Ports**: `5432:5432` 
- **Environment variables**: 
  - `POSTGRES_USER=taskuser` 
  - `POSTGRES_PASSWORD=taskpass` 
  - `POSTGRES_DB=taskdb` 
- **Storage**: `./pgdata:/var/lib/postgresql/data` 
- **Role**: Persistent storage of tasks. 

### 2. Java Spring Boot Application (`app`)
- **Image**: Built from `java-app/Dockerfile` 
- **Ports**: `8080:8080` 
- **Environment variables**: 
  - `SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/taskdb` 
  - `SPRING_DATASOURCE_USERNAME=taskuser` 
  - `SPRING_DATASOURCE_PASSWORD=taskpass` 
- **Role**: Provides web interface for managing tasks. 

---

## Container List
| Container | Image                     | Port Mapping | Role                                     |
|-----------|---------------------------|--------------|------------------------------------------|
| `db`      | `postgres:15-alpine`      | 5432:5432    | PostgreSQL database (persistent storage) |
| `app`     | Custom (Spring Boot JAR)  | 8080:8080    | Java Task Scheduler web app              |


-----------------------------------------------------------------------------------------
## Workflow of the application

### step 01 - Clone the repository
    git clone https://github.com/Thavisha72/22UG2-0023.git
    

    cd task-scheduler

### step 02 - Make scripts executable
    chmod +x prepare-app.sh start-app.sh stop-app.sh remove-app.sh

### step 02 - prepare the resources
    ./prepare-app.sh

### step 03 - start containers
    ./start-app.sh

### step 04 - Open browser → http://localhost:8080

- The App is available at http://localhost:8080

### step 05 - Add a task in the app

- In the browser, add a task (e.g., “Assignment deadline at 5 PM”)

- Task shows immediately in the task list

### step 06 - Stop services
    ./stop-app.sh

### step 07 - Restart services
    ./start-app.sh
- after restarting the service then we can again access the http://localhost:8080
- And also you can see all the tasks you previously add
- which means the data is persistent
### step 08 - Stop services
    ./stop-app.sh 

### step 09 - delete services
    ./remove-app.sh

- run this command and input y if want to delete the local pg data folder
- input N if dont want to delete the local pd data folder

### ------ IMPORTANT -------

- after input "y" the local pgdata folder wont be delete because access danied, so you can delete the folder by

      sudo rm -rf ./pgdata
 
- if u want to delete the database first before remove the app use following steps
#### -- First step
       sudo rm -rf ./pgdata
#### -- Second step

      ./remove-app.sh
  - this will remove the app and pgdata folder at once
   
- to access pgdata folder manually u need to enter your device password
