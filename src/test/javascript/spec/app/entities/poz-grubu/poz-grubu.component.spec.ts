/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DefterTakipTestModule } from '../../../test.module';
import { PozGrubuComponent } from 'app/entities/poz-grubu/poz-grubu.component';
import { PozGrubuService } from 'app/entities/poz-grubu/poz-grubu.service';
import { PozGrubu } from 'app/shared/model/poz-grubu.model';

describe('Component Tests', () => {
    describe('PozGrubu Management Component', () => {
        let comp: PozGrubuComponent;
        let fixture: ComponentFixture<PozGrubuComponent>;
        let service: PozGrubuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PozGrubuComponent],
                providers: []
            })
                .overrideTemplate(PozGrubuComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PozGrubuComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PozGrubuService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PozGrubu(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pozGrubus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
