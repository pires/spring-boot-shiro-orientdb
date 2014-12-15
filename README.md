This sample project is comprehended by the following:
* Spring Boot REST controller
* OrientDB remote object persistence by means of Spring Data
* Shiro session-management with OrientDB-based authorizing realm
* Hazelcast powered session distributed persistence

# Pre-requisites

* JDK 8
* Maven 3.2.3 or newer

# Spring Data hack

We need ```spring-data-orient```for Spring Data OrientDB support.
We'll be checking out commit ```b25c54f```, the latest at the time of this writing.

```
git clone https://github.com/noskovd/spring-data-orient.git
cd spring-data-orient
git checkout b25c54f
mvn clean install
```

# Run

```
mvn clean package spring-boot:run
```

# Testing

## REST API (JSON)

### Initialize test scenario

```
curl -i -H "Accept: application/json" -X PUT http://localhost:8080/users
```

### Access protected method without being authenticated

```
curl -i -H "Accept: application/json" -X GET http://localhost:8080/users
```

You should get a ```401 Unauthorized``` response status.

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

### Access another protected method to which you don't have permission

```
curl -i -b cookie.txt -H "Accept: application/json" -X GET http://localhost:8080/users/do_something
```

You should get a ```401 Unauthorized``` response status.
