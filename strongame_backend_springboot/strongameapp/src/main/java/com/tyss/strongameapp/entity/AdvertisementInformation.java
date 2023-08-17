package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "advertisement_information")
public class AdvertisementInformation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "advertisement_id")
	private int advertisementId;

	@Column(name = "advertisement_description", length = 999)
	private String advertisementDescription;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "advertisement_id", referencedColumnName = "advertisement_id")
    private List<AdvertisementImage> advertisementImage;

	public AdvertisementInformation() {
		super();
	}

	public AdvertisementInformation(int advertisementId, String advertisementDescription,
			List<AdvertisementImage> advertisementImage) {
		super();
		this.advertisementId = advertisementId;
		this.advertisementDescription = advertisementDescription;
		this.advertisementImage = advertisementImage;
	}

}