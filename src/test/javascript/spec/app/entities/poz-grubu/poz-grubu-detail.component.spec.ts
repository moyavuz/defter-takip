/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { PozGrubuDetailComponent } from 'app/entities/poz-grubu/poz-grubu-detail.component';
import { PozGrubu } from 'app/shared/model/poz-grubu.model';

describe('Component Tests', () => {
    describe('PozGrubu Management Detail Component', () => {
        let comp: PozGrubuDetailComponent;
        let fixture: ComponentFixture<PozGrubuDetailComponent>;
        const route = ({ data: of({ pozGrubu: new PozGrubu(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [PozGrubuDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PozGrubuDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PozGrubuDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pozGrubu).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
