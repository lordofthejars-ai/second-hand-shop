document.addEventListener('DOMContentLoaded', () => {
    // Drag-and-drop logic for the image upload (on the index.html page)
    const dropArea = document.getElementById('drop-area');
    const fileInput = document.getElementById('fileInput');
    const statusMessage = document.getElementById('statusMessage');

    // Prevent default drag behavior
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
        dropArea.addEventListener(eventName, preventDefaults, false);
    });

    function preventDefaults(e) {
        e.preventDefault();
        e.stopPropagation();
    }

    // Highlight drop area when file is dragged over
    ['dragenter', 'dragover'].forEach(eventName => {
        dropArea.addEventListener(eventName, () => dropArea.classList.add('highlight'));
    });

    ['dragleave', 'drop'].forEach(eventName => {
        dropArea.addEventListener(eventName, () => dropArea.classList.remove('highlight'));
    });

    // Handle file drop
    dropArea.addEventListener('drop', handleDrop);

    function handleDrop(e) {
        let dt = e.dataTransfer;
        let files = dt.files;
        handleFiles(files);
    }

    // Handle file selection when clicking the area
    dropArea.addEventListener('click', () => {
        fileInput.click();
    });

    fileInput.addEventListener('change', () => {
        const files = fileInput.files;
        handleFiles(files);
    });

    function handleFiles(files) {
        const file = files[0];
        if (file && file.type.startsWith('image/')) {
            uploadImage(file);
        } else {
            statusMessage.textContent = 'Please upload a valid image file.';
        }
    }

    // Upload image to server
    function uploadImage(file) {
        const formData = new FormData();
        formData.append('image', file);

        // Simulate server upload with Fetch API
        fetch('uploadItem', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (response.ok) {
                statusMessage.textContent = 'Image uploaded successfully!';
                // Redirect to form.html after a delay
                setTimeout(() => {
                    window.location.href = 'form.html';
                }, 1000);
            } else {
                statusMessage.textContent = 'Failed to upload image.';
            }
        })
        .catch(() => {
            statusMessage.textContent = 'Error occurred while uploading the image.';
        });
    }

    // Handle form submission on form.html page
    const productForm = document.getElementById('productForm');
    if (productForm) {
        productForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const formData = new FormData(productForm);
            const data = {
                name: formData.get('name'),
                model: formData.get('model'),
                brand: formData.get('brand'),
                description: formData.get('description'),
                price: formData.get('price')
            };

            // Simulate sending the form data to the server
            fetch('your-server-product-url', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            })
            .then(response => response.json())
            .then(data => {
                alert('Product information submitted successfully!');
                // Redirect to another page if needed
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to submit the product.');
            });
        });
    }
});
