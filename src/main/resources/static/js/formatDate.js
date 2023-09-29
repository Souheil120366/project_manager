// Function to format the date in yyyy-MM-dd format
  function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Month is zero-based
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  // Add a submit event listener to the form
  const form = document.querySelector('form');
  form.addEventListener('submit', function (event) {
    // Get the date input field
    const dateInput = document.getElementById('date');

    // Check if a date is selected
    if (dateInput.value) {
      // Parse the selected date
      const selectedDate = new Date(dateInput.value);

      // Format the date in yyyy-MM-dd format
      const formattedDate = formatDate(selectedDate);

      // Set the formatted date back into the input field
      dateInput.value = formattedDate;
    }
  });
