/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { MalzemeTakipDetailComponent } from 'app/entities/malzeme-takip/malzeme-takip-detail.component';
import { MalzemeTakip } from 'app/shared/model/malzeme-takip.model';

describe('Component Tests', () => {
    describe('MalzemeTakip Management Detail Component', () => {
        let comp: MalzemeTakipDetailComponent;
        let fixture: ComponentFixture<MalzemeTakipDetailComponent>;
        const route = ({ data: of({ malzemeTakip: new MalzemeTakip(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MalzemeTakipDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MalzemeTakipDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MalzemeTakipDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.malzemeTakip).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
