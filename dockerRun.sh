docker run --name rdf4j_dbpedia --rm -d -p 8080:8080 -e JAVA_OPTS="-Xms1g -Xmx4g" -v ~/rdf4j:/var/rdf4j -v ~/rdf4j_logs:/var/local/tomcat/logs eclipse/rdf4j-workbench:latest
docker run --name solid_journal --rm -v ~/Solid:/data -v ~/solid-config:/config  -p 3000:3000 -it solidproject/community-server:latest -c /config/css-config.json
