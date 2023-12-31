import React from 'react'

function Loading() {
  return (
	  <div className="row">
		  <div className="col-12">
			  <div className="callout callout-info">
				  <h5><i className="fas fa-info"></i> Loading...</h5>
				  <p>Please wait while we load the data.</p>
				  <div className='text-center p-5'>
					  <div className="spinner-grow text-primary m-2" role="status">
						  <span className="sr-only">Loading...</span>
					  </div>
					  <div className="spinner-grow text-primary m-2" role="status">
						  <span className="sr-only">Loading...</span>
					  </div>
					  <div className="spinner-grow text-primary m-2" role="status">
						  <span className="sr-only">Loading...</span>
					  </div>
				  </div>
			  </div>
		  </div>
	  </div>
  )
}

export default Loading