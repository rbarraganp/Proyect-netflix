package com.everis.d4i.tutorial.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.d4i.tutorial.entities.Season;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.SeasonRest;
import com.everis.d4i.tutorial.repositories.SeasonRepository;
import com.everis.d4i.tutorial.services.SeasonService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

/**
 * SEASON SERVICE IMPLEMENT
 */
@Service
public class SeasonServiceImpl implements SeasonService {

	@Autowired
	private SeasonRepository seasonRepository;

	private ModelMapper modelMapper = new ModelMapper();

	/**
	 * Return list seasonRest
	 * @param tvShowId
	 * @return List<SeasonRest>
	 * @throws NetflixException
	 */
	@Override
	public List<SeasonRest> getSeasonsByTvShow(Long tvShowId) throws NetflixException {
		return seasonRepository.findByTvShow_TvShowId(tvShowId).stream()
				.map(season -> modelMapper.map(season, SeasonRest.class)).collect(Collectors.toList());
	}


	/**
	 * Return Season by TvShowId And SeasonNumber
	 * @param tvShowId
	 * @param seasonNumber
	 * @return SeasonRest
	 * @throws NetflixException
	 */
	@Override
	public SeasonRest getSeasonByTvShowIdAndSeasonNumber(Long tvShowId, short seasonNumber) throws NetflixException {
		Season season = seasonRepository.findByTvShow_TvShowIdAndNumber(tvShowId, seasonNumber)
				.orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_SEASON));
		return modelMapper.map(season, SeasonRest.class);
	}
}
