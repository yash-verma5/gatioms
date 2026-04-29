-- GatiOMS Initial Schema Baseline
-- Captured from Hibernate 2026-04-29

CREATE TABLE contact_mech (
    contact_mech_id VARCHAR(20) NOT NULL,
    contact_mech_type_id VARCHAR(20),
    info_string VARCHAR(255),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (contact_mech_id)
) ENGINE=InnoDB;

CREATE TABLE party (
    party_id VARCHAR(20) NOT NULL,
    party_type_id VARCHAR(20),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (party_id)
) ENGINE=InnoDB;

CREATE TABLE person (
    party_id VARCHAR(20) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    gender VARCHAR(1),
    birth_date DATE,
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (party_id),
    CONSTRAINT FK_person_party FOREIGN KEY (party_id) REFERENCES party (party_id)
) ENGINE=InnoDB;

CREATE TABLE postal_address (
    contact_mech_id VARCHAR(20) NOT NULL,
    to_name VARCHAR(100),
    address1 VARCHAR(255),
    address2 VARCHAR(255),
    city VARCHAR(100),
    state_province_geo_id VARCHAR(20),
    postal_code VARCHAR(20),
    country_geo_id VARCHAR(20),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (contact_mech_id),
    CONSTRAINT FK_postal_contact FOREIGN KEY (contact_mech_id) REFERENCES contact_mech (contact_mech_id)
) ENGINE=InnoDB;

CREATE TABLE product (
    product_id VARCHAR(20) NOT NULL,
    product_type_id VARCHAR(20),
    product_name VARCHAR(255),
    description TEXT,
    is_virtual VARCHAR(1),
    is_variant VARCHAR(1),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (product_id)
) ENGINE=InnoDB;

CREATE TABLE good_identification_type (
    good_identification_type_id VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (good_identification_type_id)
) ENGINE=InnoDB;

CREATE TABLE good_identification (
    good_identification_type_id VARCHAR(20) NOT NULL,
    product_id VARCHAR(20) NOT NULL,
    id_value VARCHAR(255) NOT NULL,
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (good_identification_type_id, product_id),
    CONSTRAINT FK_goodid_type FOREIGN KEY (good_identification_type_id) REFERENCES good_identification_type (good_identification_type_id),
    CONSTRAINT FK_goodid_product FOREIGN KEY (product_id) REFERENCES product (product_id)
) ENGINE=InnoDB;

CREATE TABLE product_assoc_type (
    product_assoc_type_id VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (product_assoc_type_id)
) ENGINE=InnoDB;

CREATE TABLE product_assoc (
    product_id VARCHAR(20) NOT NULL,
    product_id_to VARCHAR(20) NOT NULL,
    product_assoc_type_id VARCHAR(20) NOT NULL,
    from_date DATETIME(6) NOT NULL,
    thru_date DATETIME(6),
    sequence_num INTEGER,
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (product_id, product_id_to, product_assoc_type_id, from_date),
    CONSTRAINT FK_assoc_parent FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT FK_assoc_child FOREIGN KEY (product_id_to) REFERENCES product (product_id),
    CONSTRAINT FK_assoc_type FOREIGN KEY (product_assoc_type_id) REFERENCES product_assoc_type (product_assoc_type_id)
) ENGINE=InnoDB;

CREATE TABLE product_store (
    product_store_id VARCHAR(20) NOT NULL,
    store_name VARCHAR(100),
    company_name VARCHAR(100),
    default_currency_uom VARCHAR(10),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (product_store_id)
) ENGINE=InnoDB;

CREATE TABLE shopify_config (
    shop_id VARCHAR(20) NOT NULL,
    shop_name VARCHAR(255),
    shopify_url VARCHAR(255),
    access_token VARCHAR(255),
    product_store_id VARCHAR(20),
    is_active VARCHAR(1),
    last_product_sync_date DATETIME(6),
    last_order_sync_date DATETIME(6),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (shop_id),
    CONSTRAINT FK_shopify_store FOREIGN KEY (product_store_id) REFERENCES product_store (product_store_id)
) ENGINE=InnoDB;

CREATE TABLE order_header (
    order_id VARCHAR(20) NOT NULL,
    order_type_id VARCHAR(20),
    status_id VARCHAR(20),
    customer_party_id VARCHAR(20),
    product_store_id VARCHAR(20),
    order_date DATETIME(6),
    grand_total DECIMAL(18,2),
    currency_uom VARCHAR(10),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (order_id),
    CONSTRAINT FK_order_customer FOREIGN KEY (customer_party_id) REFERENCES party (party_id),
    CONSTRAINT FK_order_store FOREIGN KEY (product_store_id) REFERENCES product_store (product_store_id)
) ENGINE=InnoDB;

CREATE TABLE order_item (
    order_id VARCHAR(20) NOT NULL,
    order_item_seq_id VARCHAR(20) NOT NULL,
    order_item_type_id VARCHAR(20),
    product_id VARCHAR(20),
    item_description VARCHAR(255),
    quantity DECIMAL(18,6),
    unit_price DECIMAL(18,3),
    status_id VARCHAR(20),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (order_id, order_item_seq_id),
    CONSTRAINT FK_item_header FOREIGN KEY (order_id) REFERENCES order_header (order_id),
    CONSTRAINT FK_item_product FOREIGN KEY (product_id) REFERENCES product (product_id)
) ENGINE=InnoDB;

CREATE TABLE order_item_ship_group (
    order_id VARCHAR(20) NOT NULL,
    ship_group_seq_id VARCHAR(20) NOT NULL,
    facility_id VARCHAR(20),
    contact_mech_id VARCHAR(20),
    shipment_method_type_id VARCHAR(20),
    fulfillment_type VARCHAR(20),
    status_id VARCHAR(20),
    carrier_party_id VARCHAR(20),
    tracking_number VARCHAR(100),
    estimated_ship_date DATETIME(6),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (order_id, ship_group_seq_id),
    CONSTRAINT FK_ship_header FOREIGN KEY (order_id) REFERENCES order_header (order_id),
    CONSTRAINT FK_ship_address FOREIGN KEY (contact_mech_id) REFERENCES contact_mech (contact_mech_id)
) ENGINE=InnoDB;

CREATE TABLE shopify_shop_order (
    shopify_order_id VARCHAR(50) NOT NULL,
    shop_id VARCHAR(20) NOT NULL,
    order_id VARCHAR(20),
    shopify_order_name VARCHAR(50),
    import_date DATETIME(6),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (shopify_order_id, shop_id),
    CONSTRAINT FK_bridge_header FOREIGN KEY (order_id) REFERENCES order_header (order_id),
    CONSTRAINT FK_bridge_shop FOREIGN KEY (shop_id) REFERENCES shopify_config (shop_id)
) ENGINE=InnoDB;

CREATE TABLE order_status_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id VARCHAR(20),
    status_id VARCHAR(20) NOT NULL,
    status_datetime DATETIME(6),
    changed_by_user_id VARCHAR(50),
    created_stamp DATETIME(6),
    last_updated_stamp DATETIME(6),
    created_by_user_login VARCHAR(50),
    last_modified_by_user_login VARCHAR(50),
    PRIMARY KEY (id),
    CONSTRAINT FK_log_header FOREIGN KEY (order_id) REFERENCES order_header (order_id)
) ENGINE=InnoDB;

CREATE INDEX idx_good_id_value ON good_identification (id_value, good_identification_type_id);
