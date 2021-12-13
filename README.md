# Message-contract-test-with-dynamic-data

### first start local kafka docker with:

```sudo docker-compose up```
### Run the contract test using below maven command

``` mvn clean install ```

### Paste the pact-broker url in browser.

```http://localhost:9292```

### After successfully Running contract test, then publish the generated pact into Pact-Broker.

```mvn pact:publish ```
