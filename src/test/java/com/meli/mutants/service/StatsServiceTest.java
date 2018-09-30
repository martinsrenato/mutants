package com.meli.mutants.service;

import com.meli.mutants.exception.StatsException;
import com.meli.mutants.model.Stats;
import com.meli.mutants.repository.MutantRepository;
import com.meli.mutants.service.impl.StatsServiceImpl;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.jooq.impl.DSL.field;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatsServiceTest {

    @Mock
    MutantRepository mutantRepository;

    @InjectMocks
    StatsServiceImpl statsService;

    @Test
    public void getStats() {
        when(mutantRepository.getStats()).thenReturn(mockResult());

        Stats stats = statsService.getStats();

        assertNotNull(stats);
        assertEquals(40, (long)stats.getCountMutantDna());
        assertEquals(100, (long)stats.getCountHumanDna());
        assertEquals(0.4, stats.getRatio(), 0);

    }

    @Test(expected = StatsException.class)
    public void errorGettingStats() {
        when(mutantRepository.getStats()).thenThrow(new RuntimeException("test"));
        statsService.getStats();
    }

    private Result<Record2<Integer, Boolean>> mockResult() {
        DSLContext create = DSL.using(SQLDialect.POSTGRES);

        Field<Integer> count = field("count", Integer.class);
        Field<Boolean> isBoolean = field("is_mutant", Boolean.class);

        Result<Record2<Integer, Boolean>> result = create.newResult(count, isBoolean);
        result.add(create.newRecord(count, isBoolean).values(40, true));
        result.add(create.newRecord(count, isBoolean).values(100, false));

        return result;
    }
}


