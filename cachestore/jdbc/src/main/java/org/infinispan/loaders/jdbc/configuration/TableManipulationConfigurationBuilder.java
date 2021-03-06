/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.infinispan.loaders.jdbc.configuration;

import org.infinispan.configuration.Builder;
import org.infinispan.configuration.Self;
import org.infinispan.loaders.jdbc.TableManipulation;

/**
 * TableManipulationConfigurationBuilder.
 *
 * @author Tristan Tarrant
 * @since 5.2
 */
public abstract class TableManipulationConfigurationBuilder<B extends AbstractJdbcCacheStoreConfigurationBuilder<?, B>, S extends TableManipulationConfigurationBuilder<B, S>> extends
      AbstractJdbcCacheStoreConfigurationChildBuilder<B> implements Builder<TableManipulationConfiguration>, Self<S> {
   private int batchSize = TableManipulation.DEFAULT_BATCH_SIZE;
   private int fetchSize = TableManipulation.DEFAULT_FETCH_SIZE;
   private boolean createOnStart = true;
   private boolean dropOnExit = false;
   private String tableNamePrefix;
   private String cacheName;
   private String idColumnName;
   private String idColumnType;
   private String dataColumnName;
   private String dataColumnType;
   private String timestampColumnName;
   private String timestampColumnType;

   TableManipulationConfigurationBuilder(AbstractJdbcCacheStoreConfigurationBuilder<?, B> builder) {
      super(builder);
   }

   /**
    * When doing repetitive DB inserts (e.g. on
    * {@link org.infinispan.loaders.CacheStore#fromStream(java.io.ObjectInput)} this will be batched
    * according to this parameter. This is an optional parameter, and if it is not specified it will
    * be defaulted to {@link #DEFAULT_BATCH_SIZE}.
    */
   public S batchSize(int batchSize) {
      this.batchSize = batchSize;
      return self();
   }

   /**
    * For DB queries (e.g. {@link org.infinispan.loaders.CacheStore#toStream(java.io.ObjectOutput)}
    * ) the fetch size will be set on {@link java.sql.ResultSet#setFetchSize(int)}. This is optional
    * parameter, if not specified will be defaulted to {@link #DEFAULT_FETCH_SIZE}.
    */
   public S fetchSize(int fetchSize) {
      this.fetchSize = fetchSize;
      return self();
   }

   /**
    * Sets the prefix for the name of the table where the data will be stored. "_<cache name>" will
    * be appended to this prefix in order to enforce unique table names for each cache.
    */
   public S tableNamePrefix(String tableNamePrefix) {
      this.tableNamePrefix = tableNamePrefix;
      return self();
   }

   /**
    * Determines whether database tables should be created by the store on startup
    */
   public S createOnStart(boolean createOnStart) {
      this.createOnStart = createOnStart;
      return self();
   }

   /**
    * Determines whether database tables should be dropped by the store on shutdown
    */
   public S dropOnExit(boolean dropOnExit) {
      this.dropOnExit = dropOnExit;
      return self();
   }

   /**
    * The name of the database column used to store the keys
    */
   public S idColumnName(String idColumnName) {
      this.idColumnName = idColumnName;
      return self();
   }

   /**
    * The type of the database column used to store the keys
    */
   public S idColumnType(String idColumnType) {
      this.idColumnType = idColumnType;
      return self();
   }

   /**
    * The name of the database column used to store the entries
    */
   public S dataColumnName(String dataColumnName) {
      this.dataColumnName = dataColumnName;
      return self();
   }

   /**
    * The type of the database column used to store the entries
    */
   public S dataColumnType(String dataColumnType) {
      this.dataColumnType = dataColumnType;
      return self();
   }

   /**
    * The name of the database column used to store the timestamps
    */
   public S timestampColumnName(String timestampColumnName) {
      this.timestampColumnName = timestampColumnName;
      return self();
   }

   /**
    * The type of the database column used to store the timestamps
    */
   public S timestampColumnType(String timestampColumnType) {
      this.timestampColumnType = timestampColumnType;
      return self();
   }

   @Override
   public void validate() {
   }

   @Override
   public TableManipulationConfiguration create() {
      return new TableManipulationConfiguration(idColumnName, idColumnType, tableNamePrefix, cacheName, dataColumnName, dataColumnType, timestampColumnName, timestampColumnType,
            fetchSize, batchSize, createOnStart, dropOnExit);
   }

   @Override
   public Builder<?> read(TableManipulationConfiguration template) {
      this.batchSize = template.batchSize();
      this.fetchSize = template.fetchSize();
      this.createOnStart = template.createOnStart();
      this.dropOnExit = template.dropOnExit();
      this.idColumnName = template.idColumnName();
      this.idColumnType = template.idColumnType();
      this.dataColumnName = template.dataColumnName();
      this.dataColumnType = template.dataColumnType();
      this.timestampColumnName = template.timestampColumnName();
      this.timestampColumnType = template.timestampColumnType();
      this.cacheName = template.cacheName();
      this.tableNamePrefix = template.tableNamePrefix();

      return this;
   }
}
