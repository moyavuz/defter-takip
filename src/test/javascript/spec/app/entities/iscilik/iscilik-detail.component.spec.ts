/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { IscilikDetailComponent } from 'app/entities/iscilik/iscilik-detail.component';
import { Iscilik } from 'app/shared/model/iscilik.model';

describe('Component Tests', () => {
    describe('Iscilik Management Detail Component', () => {
        let comp: IscilikDetailComponent;
        let fixture: ComponentFixture<IscilikDetailComponent>;
        const route = ({ data: of({ iscilik: new Iscilik(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [IscilikDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IscilikDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IscilikDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.iscilik).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
