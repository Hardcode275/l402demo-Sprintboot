# L402: l402-tutorial.java

## Introduction
L402 is a protocol designed to integrate machine-friendly transactions into HTTP-based interactions. The protocol allows seamless, automated payment handling between services and clients, with a focus on automation and AI integration. This Java port is based on the original Python implementation created by **Positeblue**.


## Requirements
To run this project, you will need the following:
- Java 21.0.4 (or compatible version)
- Maven (for dependency management)
- A working HTTP server (e.g., Spring Boot) for running the API
- A payment provider like Stripe or a Lightning Network for testing

### Running the Application

To get the L402 challenge (macaroon, invoice, preimage), run:

```
curl -X GET http://localhost:8080/api/l402/challenge

```
To access the protected resource, use:
```
curl -X GET http://localhost:8080/api/l402/protected-resource ^ -H "Authorization: L402 tB4bgQZGMtZLBsJgodYpwT3yMytwglo3CazFZkZsYpo=:2f84e22556af9919f695d7761f404e98ff98058b7d32074de8c0c83bf63eecd7"
```