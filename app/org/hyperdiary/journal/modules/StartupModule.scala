package org.hyperdiary.journal.modules

import com.google.inject.{ AbstractModule, Provides, Singleton }
import org.hyperdiary.journal.config.Config
import pureconfig.*
import pureconfig.generic.semiauto.*

given ConfigReader[Config] = deriveReader

class StartupModule extends AbstractModule {

  @Provides
  @Singleton
  def configProvider: Config =
    ConfigSource.default.loadOrThrow[Config]

}
