-- GatiOMS Seed Data
-- Mandatory Reference Types

INSERT INTO good_identification_type (good_identification_type_id, description, created_stamp) 
VALUES 
('SKU', 'Stock Keeping Unit', NOW()),
('UPC', 'Universal Product Code', NOW()),
('SHOPIFY_PROD_ID', 'Shopify Product ID', NOW()),
('SHOPIFY_VAR_ID', 'Shopify Variant ID', NOW());

INSERT INTO product_assoc_type (product_assoc_type_id, description, created_stamp)
VALUES 
('PRODUCT_VARIANT', 'Link between Virtual Parent and Variant Child', NOW()),
('PRODUCT_UPGRADE', 'Link to a newer version of the product', NOW());

INSERT INTO product_store (product_store_id, store_name, company_name, default_currency_uom, created_stamp)
VALUES 
('STORE_1', 'Main Gati Store', 'Gati Corp', 'USD', NOW());
