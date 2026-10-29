# TODO / Future Scope

While the primary MVP is fully complete and functional, the following items are left for potential future iteration:

- [ ] Connect a real email/SMS gateway (e.g. AWS SES, SendGrid, Twilio) for the notification service instead of just database records.
- [ ] Implement live GPS tracking websocket connection for the volunteer delivery view.
- [ ] Implement an image upload service (AWS S3 or Cloudinary) to replace the simple `image_url` string field for food pictures.
- [ ] Add a chat feature allowing NGO and Volunteer to communicate.
- [ ] Implement QR code generation upon donation acceptance, which the volunteer scans upon pickup to verify.
