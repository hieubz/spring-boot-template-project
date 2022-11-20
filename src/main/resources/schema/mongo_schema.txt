db.createCollection("processing_products");
db.processing_products.createIndex({created: 1}, {expireAfterSeconds: 5});
db.processing_products.createIndex({product_id: 1}, {unique: true});