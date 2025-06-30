import React from "react";

const Footer = () => {
  return (
    <footer className="bg-light text-dark mt-5 py-4 border-top">
      <div className="container">
        <div className="row text-center text-md-start align-items-center">
          <div className="col-md-6 mb-3">
            <h5>QuitQ</h5>
            <p className="small mb-0">
              Bringing quality and comfort to your doorstep.
            </p>
          </div>

          <div className="col-md-6 mb-3 text-md-end">
            <h6>Follow Us</h6>
            <a href="#" className="text-dark me-3">
              <i className="fab fa-facebook-f"></i>
            </a>
            <a href="#" className="text-dark me-3">
              <i className="fab fa-instagram"></i>
            </a>
            <a href="#" className="text-dark">
              <i className="fab fa-x-twitter"></i>
            </a>
          </div>
        </div>

        <hr className="border-secondary" />
        <div className="text-center">
          <small>
            © {new Date().getFullYear()} QuitQ. All rights reserved.
          </small>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
