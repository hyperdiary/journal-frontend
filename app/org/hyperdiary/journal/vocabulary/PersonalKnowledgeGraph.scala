package org.hyperdiary.journal.vocabulary

import org.hyperdiary.journal.config.Config
import play.api.Configuration

import javax.inject.Inject

class PersonalKnowledgeGraph @Inject(config: Config) {
  val baseUri = s"http://${config.hyperdiarySubdomain}.hyperdiary.io"
  val labelBaseUri = s"$baseUri/label/"
  val personBaseUri = s"$baseUri/person/"
  val residenceBaseUri = s"$baseUri/residence/"
  val thingBaseUri = s"$baseUri/thing/"
  val photoBaseUri = s"$baseUri/photo/"
}
