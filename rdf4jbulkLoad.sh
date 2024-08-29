# Used to manually populate a local instance of RDF4J with test data
curl -v -X DELETE http://localhost:8080/rdf4j-server/repositories/journal
curl -v -X PUT -H "Content-Type: text/turtle" --data-binary @repositoryConfig.ttl http://localhost:8080/rdf4j-server/repositories/journal
curl -v -H "Content-type: text/turtle" -T "/home/rkw/Downloads/labels_lang_8m=en.ttl" http://localhost:8080/rdf4j-server/repositories/journal/statements
curl -v -H "Connection: close" http://localhost:8080/rdf4j-server