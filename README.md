oracledb-spring-service-broker
===========================

Spring MVC framework app for V2 CloudFoundry service brokers

# Overview

This is my attempt at an OracleDB v2 Service Broker, using the Spring MVC framework app as a starting point. This framework is located here: https://github.com/cloudfoundry-community/spring-service-broker

## Compatibility

- Tested with cf-release v160 on OpenStack
- Service Broker API: v2.1

## Getting Started


### Security

When you register your broker with the cloud controller, you are prompted to enter a username and password.  This is used by the broker to verify requests.

By default, the broker uses Spring Security to protect access to resources.  The username and password are stored in: /src/main/webapp/WEB-INF/spring/security-context.xml".  By default, the password should be encoded using the Spring BCryptPasswordEncoder.  A utility class is included to provide encryption.  You can encrypt the password executing: 

`java com.pivotal.cf.broker.util.PasswordEncoder password-to-encrypt`

### Testing

Integration tests are included to test the controllers.  You are responsible for testing your service implementation.  You can run the tests with gradle (`gradle test`).

Endpoint tests using a RestTemplate are "coming soon."

### Model Notes

- The model is for the REST/Controller level.  It can be extended as needed.
- All models explicitly define serialization field names.

## To Do





