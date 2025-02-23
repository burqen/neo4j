/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.harness.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.kernel.configuration.Config;
import org.neo4j.server.configuration.ConfigurationBuilder;
import org.neo4j.server.configuration.ServerSettings;
import org.neo4j.server.configuration.ThirdPartyJaxRsPackage;

public class MapConfigurator implements ConfigurationBuilder
{
    private final Map<String, String> config;
    private final List<ThirdPartyJaxRsPackage> extensions;

    public MapConfigurator( Map<String, String> config, List<ThirdPartyJaxRsPackage> extensions )
    {
        this.config = config;
        this.extensions = extensions;
    }

    @Override
    public Map<String, String> getDatabaseTuningProperties()
    {
        return config;
    }

    @Override
    public Config configuration()
    {
        Map<String, String> serverConfigParams = new HashMap<>();
        serverConfigParams.putAll( config );
        serverConfigParams.put( ServerSettings.third_party_packages.name(),
                ConfiguratorWrappingConfigurationBuilder.toStringForThirdPartyPackageProperty( extensions ) );
        return new Config( serverConfigParams );
    }
}
