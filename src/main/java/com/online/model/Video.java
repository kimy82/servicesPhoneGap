package com.online.model;


	import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

	@Entity
	@Table( name="VIDEOS" )
	public class Video implements Serializable{



		/**
		 * 
		 */
		private static final long	serialVersionUID	= 1L;
		
		@Expose
		private Integer		id;
		
		protected byte[]	video;
		private String		descripcio;

		public Video() {

		}

		
		public Video( byte[] video ) {

			this.video = video;
		}

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "VIDEO_ID", unique = true, nullable = false)
		public Integer getId(){

			return this.id;
		}

		public void setId( Integer id ){

			this.id = id;
		}
		
		@Column(name = "VIDEO_NAME", unique = false, nullable = false, length = 200)
		public String getDescripcio(){

			return descripcio;
		}

		public void setDescripcio( String descripcio ){

			this.descripcio = descripcio;
		}

		@Column(name = "VIDEO", unique = false, nullable = false)
		public byte[] getVideo(){
		
			return video;
		}

		public void setVideo( byte[] video ){
		
			this.video = video;
		}				
}
