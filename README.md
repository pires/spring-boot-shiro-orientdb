This sample project is comprehended by the following:
* Spring Boot REST controller
* OrientDB remote object persistence by means of Spring Data
* Shiro session-management
* Hazelcast (3.2) powered session distributed persistence

# Pre-requisites

* JDK 8
* Maven 3.2.3 or newer
* OrientDB server running with a clean schema named ```orientdb-test``` and one class named ```User```.

# Spring Data hack

We need ```spring-data-orient```for Spring Data OrientDB support.
We'll be checking out commit ```f9a064ec4a```, the latest at the time of this writing.

```
git clone https://github.com/noskovd/spring-data-orient.git
cd spring-data-orient
git checkout f9a064ec4a
mvn clean install
```

# Run

```
mvn clean package spring-boot:run
```

# Testing

## REST API (JSON)

### Add user

```
curl -H "Accept: application/json" -H "Content-type: application/json" -X PUT -d '{"name":"Paulo Pires", "email":"pjpires@gmail.com", "password":"123qwe"}' http://localhost:8080/users
```

### Access protected method without being authenticated

```
curl -i -H "Accept: application/json" -X GET http://localhost:8080/users
```

You should get a ```403 Forbidden``` response status.

### Log-in

```
curl -i -c cookie.txt -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"username":"pjpires@gmail.com","password":"123qwe"}' http://localhost:8080/users/auth
```

You should get a ```200 OK``` response status and have a valid cookie stored in ```cookie.txt```.

### Access protected method again

```
curl -i -b cookie.txt -H "Accept: application/json" -X GET http://localhost:8080/users
```

You should get a ```200 OK``` response status and some JSON representing existing users.
