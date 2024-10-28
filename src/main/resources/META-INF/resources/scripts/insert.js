document.getElementById('productForm').addEventListener('submit', async function(event) {
        event.preventDefault();

        // Get form data
        const model = document.getElementById('model').value;
        const label = document.getElementById('label').value;
        const brand = document.getElementById('brand').value;
        const description = document.getElementById('description').value;
        const price = document.getElementById('price').value;
        const condition = document.getElementById('condition').value;
        const category = document.getElementById('category').value;
        const subcategory = document.getElementById('subcategory').value;

        // Prepare the data in JSON format
        const productData = {
            model: model,
            label: label,
            brand: brand,
            description: description,
            price: price,
            condition: condition,
            category: category,
            subcategory: subcategory

        };

        // Send data to the server
        try {
            const response = await fetch('update', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(productData)
            })
            .then(response => {
                // Display success or error message
                const statusMessage = document.getElementById('statusMessage');
                if (response.ok) {
                    setTimeout(() => {
                                window.location.href = 'list.html'
                    }, 1000);
                } else {
                    statusMessage.textContent = `Error: ${result.message || 'Failed to submit product'}`;
                    statusMessage.style.color = 'red';
                }
            })
            .catch(() => {
                statusMessage.textContent = 'Error occurred while uploading the image.';
            });

        } catch (error) {
            console.error('Error:', error);
            document.getElementById('statusMessage').textContent = 'An error occurred while submitting the product.';
            document.getElementById('statusMessage').style.color = 'red';
        }
    });