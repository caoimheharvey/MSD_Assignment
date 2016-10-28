# MSD_Assignment

https://www.dropbox.com/s/6c0gid44z92dsci/DesignDocument.pages?dl=0

App designed to function as a clocking system for businesses who employ hourly paid staff. This would allow two main Use Case’s from the staff member’s perspective and the Admin’s perspective. Account(business account) would have to be pre-registered and logged in on one device (for time being) in order to access any of the data or functionality. There would be an option to “register your business”.

<h3>Staff Perspective:</h3> 

Staff would log in with their unique ID number (4 digit number) and take a picture of themselves. The time this occurs is then saved along with the picture to the staff members profile. Once this has been done the system stores the start time and awaits an end time. The system already has the pre-determined hours the staff member will work and if the staff member clocks in 10 minutes late or forgets to clock out an email will be sent to themselves and the admin. The staff member has the option then to log out after the picture is taken and accepted or to view their upcoming shift on a new page. 

<h3>Admin Perspective: </h3>

Admin can log in and clock in just like the staff member. Admin status allows the user to view upcoming hours over a week long period for all staff. Admin also has the authority to change shifts for staff and add or remove a staff member from the system (to which all data linked to said staff member will be deleted). Admin can update the email to which all clocking information will be sent to. Admin can correct/update hours worked for staff. Admin can also update settings relating to the appearance of the application. 

<h3>System Operations: </h3>

Once system has been activated by the business it goes to a log in page in which the user needs to enter their pin. The pin in then checked against the Staff database and if it exists then it checks if the pin corresponds to someone of an admin status or not. If it corresponds to an admin status then the system displays the options that the admin has, if not, it allows the user to clock in, view hours, and clock out. If pin is not in system then the system returns an error message. System emails admin when someone is late to their shift or forgets to clock out. 
