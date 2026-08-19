CREATE TABLE form_contact (

    contact_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    customer_name VARCHAR(100) NOT NULL,

    subject VARCHAR(200) NOT NULL,

    email VARCHAR(255) NOT NULL,

    phone VARCHAR(20),

    message TEXT NOT NULL

);